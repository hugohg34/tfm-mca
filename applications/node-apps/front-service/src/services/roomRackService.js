import redisClient from '../redis/redisClient.js';

export const getRoomsRackByEstablishment2 = async (establishmentId) => {
    const establishmentRoomsKey = `establishment:${establishmentId}:rooms`;
    const roomKeys = await redisClient.sMembers(establishmentRoomsKey);

    const rooms = [];
    for (const roomKey of roomKeys) {
        const roomDetails = await redisClient.hGetAll(roomKey);
        rooms.push(roomDetails);
    }
    return rooms;
};

export const getRoomsRackByEstablishment = async (establishmentId) => {
    const establishmentRoomsKey = `establishment:${establishmentId}:rooms`;
    const roomKeys = await redisClient.sMembers(establishmentRoomsKey);

    const rooms = [];
    for (const roomKey of roomKeys) {
        const roomDetails = await redisClient.hGetAll(roomKey, '$');
        let roomDetWithOutUnderscore = convertKeysToCamelCase(JSON.parse(roomDetails.$));
        //let roomDetWithOutUnderscore = JSON.parse(roomDetails.$);
        rooms.push(roomDetWithOutUnderscore);

    }
    return rooms;
};

function toCamelCase(str) {
    const withoutPrefix = str.startsWith('is_') ? str.substring(3) : str;
    return withoutPrefix.replace(/_([a-z])/g, (g) => g[1].toUpperCase());

}

function convertKeysToCamelCase(obj) {
    return Object.keys(obj).reduce((acc, key) => {
        const camelCaseKey = toCamelCase(key);
        acc[camelCaseKey] = obj[key];
        return acc;
    }, {});
}
