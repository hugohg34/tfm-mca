import axios from 'axios';

const apiHost = 'http://localhost:8080';

const handlePost = async (req, res) => {
    try {
        const response = await axios.post(`${apiHost}${req.originalUrl}`, req.body);
        res.send(response.data);
    } catch (error) {
        res.status(500).send(error.message);
        console.error('handlePost', error);
    }
};

const handelGet = async (req, res) => {
    try {
        const response = await axios.get(`${apiHost}${req.originalUrl}`);
        res.send(response.data);
    } catch (error) {
        res.status(500).send(error.message);
        console.error('handelGet', error);
    }
};

const handelPut = async (req, res) => {
    try {
        const response = await axios.put(`${apiHost}${req.originalUrl}`, req.body);
        res.send(response.data);
    } catch (error) {
        res.status(500).send(error.message);
        console.error('handelPut', error);
    }
};


export default { handlePost, handelGet, handelPut }