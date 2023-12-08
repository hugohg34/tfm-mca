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
        const roomDetails = await redisClient.hGetAll(roomKey);
        // Parsear cada valor de roomDetails
        for (const key in roomDetails) {
            try {
                roomDetails[key] = JSON.parse(roomDetails[key]);
            } catch (e) {
                // Manejar error de parseo si es necesario
            }
        }
        rooms.push(roomDetails);
    }
    return rooms;
};
