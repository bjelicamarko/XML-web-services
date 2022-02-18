import { Routes } from "@angular/router";
import { MetaSearchPageComponent } from "./pages/meta-search-page/meta-search-page.component";

export const MetaSearchRoutes: Routes = [
    {
        path: "meta-pretraga",
        pathMatch: 'prefix',
        component: MetaSearchPageComponent,
    }
]