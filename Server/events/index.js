const path = require('path');
const hapi = require('hapi');
const mongoose = require('mongoose');
const _ = require('lodash');

const config = require(path.resolve('config/config'));

mongoose.connect(config.databaseUrl);

require(path.resolve('./models/users'));

const User = mongoose.model('User');

const init = async () => {
    // Create a server with a host and port
    const server = hapi.server({
        host: 'localhost',
		port:3000,
		routes: {
     validate: {
      failAction: async (request, h, err) => {
        if (process.env.NODE_ENV === 'production') {
          // In prod, log a limited error message and throw the default Bad Request error.
          console.error('ValidationError:', err.message); // Better to use an actual logger here.
          throw Boom.badRequest(`Invalid request payload input`);
        } else {
          // During development, log and respond with the full error.
          console.error(err);
          throw err;
        }
      }
    }
  }
    });

    await server.register([require('vision'), require('inert'), require('lout'), require('hapi-auth-jwt2')]);

    server.auth.strategy('jwt', 'jwt',
        {
            key: config.jwtKey,
            validate: async function (decoded, request) {
                try {
                    const user = await User
                        .findOne({email: decoded.email})
                        .exec();
                    if (!user) {
                        return {isValid: false};
                    }
                    request.user = user;
                    return {isValid: true};
                } catch (e) {
                    return {isValid: false};
                }
            },
            verifyOptions: {algorithms: ['HS256']}
        });

    server.auth.default('jwt');

    let routes = [];
    const authenticationRoutes = require(path.resolve('routes/authentication'));
    const profileRoutes = require(path.resolve('routes/profile'));
    const eventsRoutes = require(path.resolve('routes/events'));

    routes.push(authenticationRoutes);
    routes.push(profileRoutes);
    routes.push(eventsRoutes);

    routes = _.flatMapDeep(routes, (route) => {
        return route
    });

    // Add the routes
    server.route(routes);

    await server.start();
    return server;
};


init().then(server => {
    console.log('Server running at:', server.info.uri);
}).catch(error => {
    console.log(error);
});