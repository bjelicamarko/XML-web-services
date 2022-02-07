import { Role } from "./enums/role";

export interface RegistrationData {
    firstName: string,
    lastName: string,
    email: string,
    jmbg: string,
    role: Role
}