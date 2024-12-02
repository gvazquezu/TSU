import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProyectoService } from '../../services/tarea/proyecto.service';

@Component({
  selector: 'app-proyecto',
  templateUrl: './proyecto.component.html',
  styleUrls: ['./proyecto.component.css']
})
export class ProyectoComponent implements OnInit {
  proyectoForm!: FormGroup;
  proyectos: any[] = [];
  proyectoSeleccionado: any = null;

  constructor(private fb: FormBuilder, private proyectoService: ProyectoService) {}

  ngOnInit(): void {
    this.proyectoForm = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required]
    });

    this.cargarProyectos();
  }

  cargarProyectos(): void {
    this.proyectoService.getAllProyecto().subscribe(data => {
      this.proyectos = data;
    });
  }

  guardar(): void {
    if (this.proyectoSeleccionado) {
      this.proyectoService.updateProyecto(this.proyectoSeleccionado.id, this.proyectoForm.value).subscribe(() => {
        this.cargarProyectos();
        this.limpiarFormulario();
      });
    } else {
      this.proyectoService.createProyecto(this.proyectoForm.value).subscribe(() => {
        this.cargarProyectos();
        this.limpiarFormulario();
      });
    }
  }

  seleccionarProyecto(proyecto: any): void {
    this.proyectoSeleccionado = proyecto;
    this.proyectoForm.patchValue(proyecto);
  }

  eliminarProyecto(id: number): void {
    if (confirm('¿Está seguro de eliminar este proyecto?')) {
      this.proyectoService.deleteProyecto(id).subscribe(() => {
        this.cargarProyectos();
      });
    }
  }

  limpiarFormulario(): void {
    this.proyectoForm.reset();
    this.proyectoSeleccionado = null;
  }
}
