package com.fastcode.example.restcontrollers.core;

import com.fastcode.example.addons.email.application.mail.IEmailService;
import com.fastcode.example.application.core.authorization.tokenverification.ITokenVerificationAppService;
import com.fastcode.example.application.core.authorization.user.IUserAppService;
import com.fastcode.example.application.core.authorization.user.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.domain.core.authorization.tokenverification.TokenverificationEntity;
import com.fastcode.example.domain.core.authorization.user.IUserRepository;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import com.fastcode.example.security.JWTAppService;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PasswordController {

    @Autowired
    private ITokenVerificationAppService _tokenAppService;

    @Autowired
    @Qualifier("userAppService")
    private IUserAppService _userAppService;

    @Autowired
    @Qualifier("userRepository")
    private IUserRepository _userRepository;

    @Autowired
    private IEmailService _emailService;

    @Autowired
    private PasswordEncoder pEncoder;

    @Autowired
    private JWTAppService _jwtAppService;

    @Autowired
    private LoggingHelper logHelper;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
        "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        Pattern.CASE_INSENSITIVE
    );

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    @RequestMapping(
        value = "/forgot",
        method = RequestMethod.POST,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<HashMap<String, String>> processForgotPassword(
        @RequestBody ForgotPasswordInput passwordInput,
        HttpServletRequest request
    ) throws InvalidInputException {
        if (passwordInput.getEmail() == null || !validate(passwordInput.getEmail())) {
            logHelper.getLogger().error("Email is not valid");
            throw new InvalidInputException("Email is not valid");
        }

        FindUserByNameOutput foundUser = _userAppService.findByEmailAddress(passwordInput.getEmail());
        Optional
            .ofNullable(foundUser)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("There does not exist a user with a email=%s", passwordInput.getEmail())
                    )
            );

        String appUrl = request.getScheme() + "://" + request.getServerName();
        System.out.println("App url " + appUrl);

        TokenverificationEntity token = _tokenAppService.generateToken("password", foundUser.getId());

        String subject = "Password Reset Request";
        String emailText =
            "To reset your password, click the link below:\n" +
            passwordInput.getClientUrl() +
            "/reset-password?token=" +
            token.getToken();
        _emailService.sendEmail(_emailService.buildEmail(passwordInput.getEmail(), subject, emailText));

        String msg = "A password reset link has been sent to " + passwordInput.getEmail();
        HashMap resultMap = new HashMap<String, String>();
        resultMap.put("message", msg);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @RequestMapping(
        value = "/reset",
        method = RequestMethod.POST,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<HashMap<String, String>> setNewPassword(@RequestBody ResetPasswordInput input) {
        TokenverificationEntity tokenEntity = _tokenAppService.findByTokenAndType(input.getToken(), "password");
        Optional
            .ofNullable(tokenEntity)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid password reset link.")));

        FindUserWithAllFieldsByIdOutput output = _userAppService.findWithAllFieldsById(tokenEntity.getUserId());
        Optional
            .ofNullable(output)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid password reset link.")));

        if (new Date().after(tokenEntity.getExpirationTime())) {
            logHelper.getLogger().error("Token has expired, please request a new password reset");
            throw new EntityNotFoundException(String.format("Token has expired, please request a new password reset"));
        }

        output.setPassword(pEncoder.encode(input.getPassword()));
        _tokenAppService.deleteToken(tokenEntity);
        _userAppService.updateUserData(output);
        _jwtAppService.deleteAllUserTokens(output.getUserName());

        String msg = "Password reset successfully !";
        HashMap resultMap = new HashMap<String, String>();
        resultMap.put("message", msg);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @RequestMapping(
        value = "/update",
        method = RequestMethod.POST,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<HashMap<String, String>> changePassword(@RequestBody UpdatePasswordInput input)
        throws InvalidInputException {
        UserEntity loggedInUser = _userAppService.getUser();
        if (!pEncoder.matches(input.getOldPassword(), loggedInUser.getPassword())) {
            logHelper.getLogger().error("Invalid Old password");
            throw new InvalidInputException(String.format("Invalid Old password"));
        }
        if (pEncoder.matches(input.getNewPassword(), loggedInUser.getPassword())) {
            logHelper.getLogger().error("You cannot set prevoius password again");
            throw new InvalidInputException(String.format("You cannot set prevoius password again"));
        }

        loggedInUser.setPassword(pEncoder.encode(input.getNewPassword()));
        _userRepository.save(loggedInUser);
        _jwtAppService.deleteAllUserTokens(loggedInUser.getUserName());
        String msg = "Password updated successfully !";
        HashMap resultMap = new HashMap<String, String>();
        resultMap.put("message", msg);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }
}
