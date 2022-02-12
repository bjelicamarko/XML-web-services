import { Routes } from "@angular/router";

import { ConsentPageComponent } from "./pages/consent-page/consent-page.component";
import { TestPageComponent } from "./pages/test-page/test-page.component";

export const ConsentRoutes: Routes = [
    {
        path: "nova-saglasnost",
        pathMatch: 'prefix',
        component: ConsentPageComponent,
    },
    {
        path: "test",
        pathMatch: 'prefix',
        component: TestPageComponent,
    }
]
