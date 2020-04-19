const http = require('http');
const morgan = require('morgan');
const express = require('express');
const webServerConfig = require('../config/web-server.js');

let httpServer;

function initialize() {
    return new Promise((resolve, reject) => {
        const app = express();
        httpServer = http.createServer(app);

        app.use(morgan('combined'));
        app.get('/', (req, res) => {
            res.end('Hello World!');
        });

        httpServer.listen(webServerConfig.port)
            .on('listening', () => {
                console.log(`Web server listening on localhost:${webServerConfig.port}`);

                resolve();
            })
            .on('error', err => {
                reject(err);
            });
    });
}

function close() {
    return new Promise((resolve, reject) => {
        httpServer.close((err) => {
            if (err) {
                reject(err);
                return;
            }

            resolve();
        });
    });
}

module.exports.close = close;

module.exports.initialize = initialize;