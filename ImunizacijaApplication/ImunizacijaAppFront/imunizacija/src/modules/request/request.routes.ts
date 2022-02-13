import { Routes } from "@angular/router";

import { RequestPageComponent } from "./pages/request-page/request-page.component";

export const RequestRoutes: Routes = [
    {
        path: "nov-zahtev",
        pathMatch: 'prefix',
        component: RequestPageComponent,
    }
]