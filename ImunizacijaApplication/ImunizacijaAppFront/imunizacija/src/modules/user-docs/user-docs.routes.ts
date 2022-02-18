import { Routes } from "@angular/router";
import { CitizenDocViewComponent } from "./pages/citizen-doc-view/citizen-doc-view.component";


export const UserDocsRoutes: Routes = [
    {
        path: "dokumenti",
        pathMatch: 'prefix',
        component: CitizenDocViewComponent,
    }
]