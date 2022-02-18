import { UserViewDTO } from "./user-view-dto";

export interface UsersViewDTOList {
    users: {
        korisnikBasicInfoDTOList: UserViewDTO[];
    }
}