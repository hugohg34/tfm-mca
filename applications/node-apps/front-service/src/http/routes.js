import express from "express";
import { handelGet, handlePost, handelPut } from "./roomRackProxyController.js";
import { getRoomsRackByEstablishmentController } from "./roomRackController.js";

const router = express.Router();

router.get('/establishments/:establishmentId/rooms', getRoomsRackByEstablishmentController);
router.get('/api/v1/*', handelGet);
router.post('/*', handlePost);
router.put('/*', handelPut);

export default router;
