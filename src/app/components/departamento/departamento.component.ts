import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DepartamentoService } from '../../services/tarea/departamento.service';

@Component({
  selector: 'app-departamento',
  templateUrl: './departamento.component.html',
  styleUrls: ['./departamento.component.css']
})
export class DepartamentoComponent implements OnInit {
  departamentoForm!: FormGroup;
  departamentos: any[] = [];
  departamentoSeleccionado: any = null;

  constructor(private fb: FormBuilder, private departamentoService: DepartamentoService) {}

  ngOnInit(): void {
    this.departamentoForm = this.fb.group({
      nombre: ['', Validators.required]
    });

    this.cargarDepartamentos();
  }

  cargarDepartamentos(): void {
    this.departamentoService.getAllDepartamento().subscribe(data => {
      this.departamentos = data;
    });
  }

  guardar(): void {
    if (this.departamentoSeleccionado) {
      this.departamentoService.updateDepartamento(this.departamentoSeleccionado.id, this.departamentoForm.value).subscribe(() => {
        this.cargarDepartamentos();
        this.limpiarFormulario();
      });
    } else {
      this.departamentoService.createDepartamento(this.departamentoForm.value).subscribe(() => {
        this.cargarDepartamentos();
        this.limpiarFormulario();
      });
    }
  }

  seleccionarDepartamento(departamento: any): void {
    this.departamentoSeleccionado = departamento;
    this.departamentoForm.patchValue(departamento);
  }

  eliminarDepartamento(id: number): void {
    if (confirm('¿Está seguro de eliminar este departamento?')) {
      this.departamentoService.deleteDepartamento(id).subscribe(() => {
        this.cargarDepartamentos();
      });
    }
  }

  limpiarFormulario(): void {
    this.departamentoForm.reset();
    this.departamentoSeleccionado = null;
  }
}

