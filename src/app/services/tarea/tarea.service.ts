import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TareaService {
  private API_SERVER = 'http://localhost:8080/api/tarea';

  constructor(private httpClient: HttpClient) {}

  getAllTarea(): Observable<any> {
    return this.httpClient.get(this.API_SERVER);
  }

  createTarea(tarea: any): Observable<any> {
    return this.httpClient.post(this.API_SERVER, tarea);
  }

  updateTarea(id: number, tarea: any): Observable<any> {
    return this.httpClient.put(`${this.API_SERVER}/${id}`, tarea);
  }

  deleteTarea(id: number): Observable<any> {
    return this.httpClient.delete(`${this.API_SERVER}/${id}`);
  }

  getFilteredTareas(filters: any): Observable<any[]> {
    let params = new HttpParams();
    if (filters.nombreTarea) params = params.set('nombreTarea', filters.nombreTarea);
    if (filters.responsable) params = params.set('responsable', filters.responsable);
    if (filters.proyecto) params = params.set('proyecto', filters.proyecto);
    if (filters.departamento) params = params.set('departamento', filters.departamento);
    if (filters.puesto) params = params.set('puesto', filters.puesto);

    return this.httpClient.get<any[]>(`${this.API_SERVER}/filtrar`, { params });
  }
}
