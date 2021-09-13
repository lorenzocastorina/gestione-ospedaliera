import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BodyComponent } from './body/body.component';
import { HeaderLogoutComponent } from './header-logout/header-logout.component';
import { HeaderComponent } from './header/header.component';
import { HelpComponent } from './help/help.component';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { PazientiComponent } from './pazienti/pazienti.component';
import { StanzeComponent } from './stanze/stanze.component';




const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full'},
  { path: 'login', component: LoginComponent },
  { path: 'soccorso', component: BodyComponent },
  { path: 'stanze', component: StanzeComponent },
  { path: 'pazienti', component: PazientiComponent },
  { path: 'aiuto', component: HelpComponent },
  { path: "**", component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
