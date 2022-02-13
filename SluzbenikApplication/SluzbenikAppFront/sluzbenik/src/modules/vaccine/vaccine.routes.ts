import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { VaccinePageComponent } from "./pages/vaccine-page/vaccine-page.component";

export const VaccineRoutes: Routes = [
    {
        path: "status-vakcina",
        pathMatch: 'prefix',
        component: VaccinePageComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: "MEDICAL_OFFICIAL" }
    }
]