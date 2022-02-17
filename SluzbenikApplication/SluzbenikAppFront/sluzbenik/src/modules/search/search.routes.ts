import { Routes } from "@angular/router";
import { RoleGuard } from "../auth/guards/role/role.guard";
import { SearchPageComponent } from "./pages/search-page/search-page.component";

export const SearchRoutes: Routes = [
    {
        path: "basic-search",
        pathMatch: 'prefix',
        component: SearchPageComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: "MEDICAL_OFFICIAL" }
    }
]