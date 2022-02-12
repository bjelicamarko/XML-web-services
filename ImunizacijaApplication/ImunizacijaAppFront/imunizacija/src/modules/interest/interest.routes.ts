import { Routes } from "@angular/router";

import { InterestPageComponent } from "./pages/interest-page/interest-page.component";

export const InterestRoutes: Routes = [
    {
        path: "novo-interesovanje",
        pathMatch: 'prefix',
        component: InterestPageComponent,
    }
]