import express from 'express';
import path from 'path';
import { __dirname } from '../dirname.js';
import routes from './routes.js';

const app = express();

app.use(express.json());

app.use('/api/v1', routes);

app.use(express.static(path.join(__dirname, '../frontend/dist')));

app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, '../frontend/dist/index.html'));
});

export default app;