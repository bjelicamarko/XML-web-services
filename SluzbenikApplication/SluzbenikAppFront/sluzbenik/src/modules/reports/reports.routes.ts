import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { ReportsPageComponent } from "./pages/reports-page/reports-page.component";

export const ReportsRoutes: Routes = [
    {
        path: "pregled-izvestaja",
        pathMatch: 'prefix',
        component: ReportsPageComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: "MEDICAL_OFFICIAL" }
    }
]