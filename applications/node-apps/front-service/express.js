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

// Config proxy

httpServer.get('/api/v1/*', async (req, res) => {
    try {
        const response = await axios.get(`${apiHost}${req.originalUrl}`);
        res.send(response.data);
    } catch (error) {
        console.log(error);
        res.status(500).send(error.message);
    }
});

httpServer.post('/api/v1/*', async (req, res) => {
    try {
        const response = await axios.post(`${apiHost}${req.originalUrl}`, req.body);
        res.send(response.data);
    } catch (error) {
        res.status(500).send(error.message);
    }
});

httpServer.put('/api/v1/*', async (req, res) => {
    try {
        const response = await axios.put(`${apiHost}${req.originalUrl}`, req.body);
        res.send(response.data);
    } catch (error) {
        res.status(500).send(error.message);
    }
});

