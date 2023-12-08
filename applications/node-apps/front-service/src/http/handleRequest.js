import redisClient from '../redis/redisClient.js';
import axios from 'axios';

const apiHost = 'http://localhost:8080';


export const handlePost = async (req, res) => {
    try {
        const response = await axios.post(`${apiHost}${req.originalUrl}`, req.body);
        res.send(response.data);
    } catch (error) {
        res.status(500).send(error.message);
    }
};

export const handelGet = async (req, res) => {
    try {
        const response = await axios.get(`${apiHost}${req.originalUrl}`);
        res.send(response.data);
    } catch (error) {
        console.log(error);
        res.status(500).send(error.message);
    }
};

export const handelPut = async (req, res) => {
    try {
        const response = await axios.put(`${apiHost}${req.originalUrl}`, req.body);
        res.send(response.data);
    } catch (error) {
        res.status(500).send(error.message);
    }
};
