import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { APP_PROVIDERS } from './app/app.config';

// Bootstraps the root standalone AppComponent and applies providers configured in app.config
bootstrapApplication(AppComponent, {
  providers: APP_PROVIDERS
}).catch(err => {
  console.error('Angular bootstrap error:', err);
  console.error('Error message:', err?.message);
  console.error('Error stack:', err?.stack);

  // Show error in DOM as fallback
  const appRoot = document.querySelector('app-root');
  if (appRoot) {
    appRoot.innerHTML = `
      <div style="padding: 20px; background: #ffebee; color: #c62828; border-radius: 4px; margin: 20px;">
        <h3>⚠️ Application Error</h3>
        <p>Failed to bootstrap Angular application</p>
        <pre style="background: #fff; padding: 10px; border-radius: 4px; overflow: auto; font-size: 12px;">
${err?.message || 'Unknown error'}
        </pre>
        <p style="font-size: 12px; color: #999;">Check browser console (F12) for more details</p>
      </div>
    `;
  }
});

