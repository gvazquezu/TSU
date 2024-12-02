import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PuestoService } from '../../services/tarea/puesto.service';

@Component({
  selector: 'app-puesto',
  templateUrl: './puesto.component.html',
  styleUrls: ['./puesto.component.css']
})
export class PuestoComponent implements OnInit {
  puestoForm!: FormGroup;
  puestos: any[] = [];
  puestoSeleccionado: any = null;

  constructor(private fb: FormBuilder, private puestoService: PuestoService) {}

  ngOnInit(): void {
    this.puestoForm = this.fb.group({
      nombre: ['', Validators.required]
    });

    this.cargarPuestos();
  }

  cargarPuestos(): void {
    this.puestoService.getAllPuesto().subscribe(data => {
      this.puestos = data;
    });
  }

  guardar(): void {
    if (this.puestoSeleccionado) {
      this.puestoService.updatePuesto(this.puestoSeleccionado.id, this.puestoForm.value).subscribe(() => {
        this.cargarPuestos();
        this.limpiarFormulario();
      });
    } else {
      this.puestoService.createPuesto(this.puestoForm.value).subscribe(() => {
        this.cargarPuestos();
        this.limpiarFormulario();
      });
    }
  }

  seleccionarPuesto(puesto: any): void {
    this.puestoSeleccionado = puesto;
    this.puestoForm.patchValue(puesto);
  }

  eliminarPuesto(id: number): void {
    if (confirm('¿Está seguro de eliminar este puesto?')) {
      this.puestoService.deletePuesto(id).subscribe(() => {
        this.cargarPuestos();
      });
    }
  }

  limpiarFormulario(): void {
    this.puestoForm.reset();
    this.puestoSeleccionado = null;
  }
}


