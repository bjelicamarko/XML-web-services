import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";

import { ConsentPageComponent } from "./pages/consent-page/consent-page.component";
import { MedicalConsentPageComponent } from "./pages/medical-consent-page/medical-consent-page.component";

export const ConsentRoutes: Routes = [
    {
        path: "nova-saglasnost",
        pathMatch: 'prefix',
        canActivate: [RoleGuard],
        data: { expectedRoles: "CITIZEN" },
        component: ConsentPageComponent,
    },
    {
        path: "drugi-deo-saglasnosti",
        pathMatch: 'prefix',
        canActivate: [RoleGuard],
        data: { expectedRoles: "DOCTOR" },
        component: MedicalConsentPageComponent,
    },
]
