var Hapi = require('hapi');
var server = new Hapi.Server();
var Joi = require('joi');
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/jobs');

var UserModel = require('./models/user');


server.connection({port: 7002});

server.register({
    register: require('hapi-swagger'),
    options: {
        apiVersion: "0.0.1"
    }
}, function (err) {
    if (err) {
        server.log(['error'], 'hapi-swagger load error: ' + err)
    } else {
        server.log(['start'], 'hapi-swagger interface loaded')
    }
});


server.route({
    method: 'GET',      
    path: '/api/user',  
    handler: function (request, reply) { 

        
        reply({
            statusCode: 200,
            message: 'Getting All User Data',
            data: [
                {
                    name:'Maria',
                    email:'24',
					password:'23'
                },
                {
                    name:'Eliane',
                    email:'24',
					password:'23'
                },
                {
                    name:'Jasmine',
                    email:'24',
					password:'23'
                }
            ]
        });
    }
});

server.route({
    method: 'POST',
    path: '/api/user',
    config: {
        
        tags: ['api'],
        description: 'Save user data',
        notes: 'Save user data',
        validate: {
            payload: {
                name: Joi.string().required(),
                email: Joi.number().required(),
				password: Joi.number().required()
            }
        }
    },
    handler: function (request, reply) {
        var user = new UserModel(request.payload);
        user.save(function (error) {
            if (error) {
                reply({
                    statusCode: 503,
                    message: error
                });
            } else {
                reply({
                    statusCode: 201,
                    message: 'User Saved Successfully'
                });
            }
        });
    }
});

server.route({
    method: 'GET',
    path: '/api/user',
    config: {
        tags: ['api'],
        description: 'Get All User data',
        notes: 'Get All User data'
    },
    handler: function (request, reply) {
        UserModel.find({}, function (error, data) {
            if (error) {
                reply({
                    statusCode: 503,
                    message: 'Failed to get data',
                    data: error
                });
            } else {
                reply({
                    statusCode: 200,
                    message: 'User Data Successfully Fetched',
                    data: data
                });
            }
        });
    }
});

server.route({
    method: 'GET',
    path: '/api/user/{name}',
    config: {
        tags: ['api'],
        description: 'Get specific user data',
        notes: 'Get specific user data',
        validate: {
            params: {
                name: Joi.string().required()
            }
        }
    },
    handler: function (request, reply) {

        UserModel.find({_id: request.params.id}, function (error, data) {
            if (error) {
                reply({
                    statusCode: 503,
                    message: 'Failed to get data',
                    data: error
                });
            } else {
                if (data.length === 0) {
                    reply({
                        statusCode: 200,
                        message: 'User Not Found',
                        data: data
                    });
                } else {
                    reply({
                        statusCode: 200,
                        message: 'User Data Successfully Fetched',
                        data: data
                    });
                }
            }
        });
    }
});
server.route({
    method: 'PUT',
    path: '/api/user/{name}',
    config: {
        tags: ['api'],
        description: 'Update specific user data',
        notes: 'Update specific user data',

        validate: {
            params: {
                name: Joi.string().required()
            },
            payload: {
                name: Joi.string(),
                email: Joi.string(),
				password: Joi.string()
            }
        }
    },
    handler: function (request, reply) {
        UserModel.findOneAndUpdate({_id: request.params.id}, request.payload, function (error, data) {
            if (error) {
                reply({
                    statusCode: 503,
                    message: 'Failed to get data',
                    data: error
                });
            } else {
                reply({
                    statusCode: 200,
                    message: 'User Updated Successfully',
                    data: data
                });
            }
        });

    }
});

server.route({
    method: 'DELETE',
    path: '/api/user/{name}',
    config: {
        tags: ['api'],
        description: 'Remove specific user data',
        notes: 'Remove specific user data',
        validate: {
            params: {
                name: Joi.string().required()
            }
        }
    },
    handler: function (request, reply) {

        UserModel.findOneAndRemove({_id: request.params.id}, function (error) {
            if (error) {
                reply({
                    statusCode: 503,
                    message: 'Error in removing User',
                    data: error
                });
            } else {
                reply({
                    statusCode: 200,
                    message: 'User Deleted Successfully'
                });
            }
        });

    }
});


server.start(function () {
    console.log('Server running at:', server.info.uri);
});