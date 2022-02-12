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
      }
    ]
  },
  // {
  //   path: "",
  //   redirectTo: "rest-app/auth/login",
  //   pathMatch: "full",
  // },
  { path: "**", component: NotFoundPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
