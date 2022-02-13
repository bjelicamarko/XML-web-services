import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { RegistrationPageComponent } from './pages/registration-page/registration-page.component';
import { RootLayoutPageComponent } from './pages/root-layout-page/root-layout-page.component';

const routes: Routes = [
  {
    path: "imunizacija-app",
    component: RootLayoutPageComponent,
    children: [
      {
        path: "auth",
        loadChildren: () =>
          import("./../auth/auth.module").then((m) => m.AuthModule),
      },
      {
        path: "register",
        component: RegistrationPageComponent,
      },
      {
        path: "interesovanje",
        loadChildren: () =>
          import("./../interest/interest.module").then((m) => m.InterestModule),
      },
      {
        path: "saglasnost",
        loadChildren: () =>
          import("./../consent/consent.module").then((m) => m.ConsentModule),
      },
      {
        path: "zahtev",
        loadChildren: () => 
          import("./../request/request.module").then((m) => m.RequestModule),
      }
    ]
  },
  {
    path: "",
    redirectTo: "imunizacija-app/auth/login",
    pathMatch: "full",
  },
  { path: "**", component: NotFoundPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
