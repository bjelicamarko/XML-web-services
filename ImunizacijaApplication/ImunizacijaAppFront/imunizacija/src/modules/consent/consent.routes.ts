import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";

import { ConsentPageComponent } from "./pages/consent-page/consent-page.component";

export const ConsentRoutes: Routes = [
    {
        path: "nova-saglasnost",
        pathMatch: 'prefix',
        component: ConsentPageComponent,
    },
]
