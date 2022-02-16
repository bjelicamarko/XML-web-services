import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";

import { InterestPageComponent } from "./pages/interest-page/interest-page.component";
import { TestPageComponent } from "./pages/test-page/test-page.component";

export const InterestRoutes: Routes = [
    {
        path: "novo-interesovanje",
        pathMatch: 'prefix',
        component: InterestPageComponent, //todo dodati role guardove svuda
        canActivate: [RoleGuard],
        data: { expectedRoles: "CITIZEN" },
    },
    {
        path: "test",
        pathMatch: 'prefix',
        component: TestPageComponent
    }
]