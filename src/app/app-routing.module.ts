import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TareaComponent } from './components/tarea/tarea.component';
import { ResponsableComponent } from './components/responsable/responsable.component';
import { ProyectoComponent } from './components/proyecto/proyecto.component';
import { DepartamentoComponent } from './components/departamento/departamento.component';
import { PuestoComponent } from './components/puesto/puesto.component';

const routes: Routes = [
  { path: 'tarea', component: TareaComponent },
  { path: 'responsable', component: ResponsableComponent },
  { path: 'proyecto', component: ProyectoComponent },
  { path: 'departamento', component: DepartamentoComponent },
  { path: 'puesto', component: PuestoComponent },
  { path: '**', redirectTo: 'tarea' }
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
