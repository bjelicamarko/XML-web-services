import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { UsersViewComponent } from "./pages/users-view/users-view.component";

export const VaccineRoutes: Routes = [
    {
        path: "izdavanje",
        pathMatch: 'prefix',
        component: UsersViewComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: "MEDICAL_OFFICIAL" }
    }
]
