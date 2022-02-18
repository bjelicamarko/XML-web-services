import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { CitizenDocViewComponent } from "./pages/citizen-doc-view/citizen-doc-view.component";
import { UsersViewComponent } from "./pages/users-view/users-view.component";

export const IssueDZSRoutes: Routes = [
    {
        path: "izdavanje",
        pathMatch: 'prefix',
        component: UsersViewComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: "MEDICAL_OFFICIAL" }
    },
    {
        path: "dokumenti/:userID",
        pathMatch: 'prefix',
        component: CitizenDocViewComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: "MEDICAL_OFFICIAL" }
    }
]
