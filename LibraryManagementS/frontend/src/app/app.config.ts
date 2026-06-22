import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { provideAnimations } from '@angular/platform-browser/animations';
import { routes } from './app.routes';

// Base API URL for the backend
export const API_BASE = 'http://localhost:8080';

// App-level providers. We provide HttpClient and the Router here so the standalone
// components can inject HttpClient and use routing.
export const APP_PROVIDERS = [
  provideHttpClient(),
  provideRouter(routes),
  provideAnimations()
];

