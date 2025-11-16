import {InjectionToken} from "@angular/core";
import {BackendServerSettingsInterface} from "./backend-server-settings.interface";
import {environment} from "../environments/environment";

export const BACKEND_SERVER_SETTINGS = new InjectionToken<BackendServerSettingsInterface>(
  'backend_server',
  {
    providedIn: null,
    factory: ()=> environment,
  }
);
