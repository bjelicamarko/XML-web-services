import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { RootLayoutPageComponent } from './pages/root-layout-page/root-layout-page.component';

const routes: Routes = [
  {
    path: "sluzbenik-app",
    component: RootLayoutPageComponent,
    children: [
      {
        path: "auth",
        loadChildren: () =>
          import("./../auth/auth.module").then((m) => m.AuthModule),
      },
      {
        path: "vakcina",
        loadChildren: () =>
          import("./../vaccine/vaccine.module").then((m) => m.VaccineModule),
      },
      {
        path: "dzs",
        loadChildren: () =>
          import("./../issue-dzs/issue-dzs.module").then((m) => m.IssueDzsModule),
      }
    ]
  },
  {
    path: "",
    redirectTo: "sluzbenik-app/auth/login",
    pathMatch: "full",
  },
  { path: "**", component: NotFoundPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
