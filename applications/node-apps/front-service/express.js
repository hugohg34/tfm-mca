import express from 'express';
import path from 'path';
import axios from 'axios';

import { __dirname } from './dirname.js';

export const httpServer = express();

const apiHost = 'http://localhost:8080';

httpServer.use(express.json());
httpServer.use(express.static(path.join(__dirname, 'frontend/dist')));

httpServer.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'frontend/dist/index.html'));
});
