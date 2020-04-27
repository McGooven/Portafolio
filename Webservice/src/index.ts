import express from "express";
import morgan from "morgan";
import cors from "cors";
import { createConnections,Connection } from "typeorm";
import routes from "./routes/generalRoutes";

const app = express();

async function initConnection() {
    const connections: Connection[] = await createConnections();
}
initConnection();

//Middlewares
app.use(cors());
app.use(morgan('dev'));
app.use(express.json());

//Routes
app.use('/api',routes);


app.listen(3000);
console.log('server on port',3000);

