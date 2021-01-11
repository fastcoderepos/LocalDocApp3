export interface IUserpermission {
  permissionId: number;
  revoked?: boolean;
  userId: number;

  permissionDescriptiveField?: string;
  userDescriptiveField?: string;
}
