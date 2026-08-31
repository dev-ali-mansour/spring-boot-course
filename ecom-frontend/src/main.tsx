import {StrictMode} from 'react';
import {createRoot} from 'react-dom/client';
import './index.css';
import App from './App';
import {Provider} from "react-redux";
import {store} from "./store/reducers/store";
import {BrowserRouter} from "react-router-dom";

createRoot(document.getElementById('root') as HTMLElement).render(
    <Provider store={store}>
        <StrictMode>
            <BrowserRouter>
                <App/>
            </BrowserRouter>
        </StrictMode>
    </Provider>
);
