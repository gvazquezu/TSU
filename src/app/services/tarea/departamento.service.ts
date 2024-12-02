import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class DepartamentoService {
  private API_SERVER = 'http://localhost:8080/api/departamento';

  constructor(private httpClient: HttpClient) {}

  getAllDepartamento(): Observable<any> {
    return this.httpClient.get(this.API_SERVER);
  }

  createDepartamento(departamento: any): Observable<any> {
    return this.httpClient.post(this.API_SERVER, departamento);
  }

  updateDepartamento(id: number, departamento: any): Observable<any> {
    return this.httpClient.put(`${this.API_SERVER}/${id}`, departamento);
  }

  deleteDepartamento(id: number): Observable<any> {
    return this.httpClient.delete(`${this.API_SERVER}/${id}`);
  }

  public getFilteredDepartamentos(filters: any): Observable<any[]> {
    let params = new HttpParams();
    if (filters.nombre) params = params.set('nombre', filters.nombre);

    return this.httpClient.get<any[]>(`${this.API_SERVER}/filtrar`, { params });
  }
}
