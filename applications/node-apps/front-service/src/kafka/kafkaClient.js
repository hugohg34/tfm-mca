import { Kafka, logLevel } from 'kafkajs';
import ip from 'ip';

const host = process.env.HOST_IP || ip.address();
const port = process.env.HOST_PORT || 29092;

const kafka = new Kafka({
    logLevel: logLevel.INFO,
    brokers: [`${host}:${port}`],
    clientId: 'front-service',
});

export default kafka;