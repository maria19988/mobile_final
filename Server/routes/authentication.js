const path = require('path');
const Boom = require('boom');
const mongoose = require('mongoose');
const jwt = require('jsonwebtoken');
const joi = require('joi');


const config = require(path.resolve('config/config'));
const UserJobSchema = require(path.resolve('models/jobs'));
const UserJobS = require(path.resolve('models/userJob'));
const User = mongoose.model('User');
const Job = mongoose.model('Job');
const UserJob = mongoose.model('UserJob');
const Utils = require(path.resolve('./utils/utils'));

const endpoints = [
    {
        method: 'POST',
        path: '/register',
        config: {
            auth: false,
            validate: {
                payload: {
                    name: joi.string().min(5).required(),
                    email: joi.string().email().required(),
                    password: joi.string().required()
                }
            }
        },
        handler: async function (request, h) {
            try {
                const newUser = new User(request.payload);
                const result = await newUser.save();
                return Utils.sanitizeUser(result);
            } catch (e) {
                return Boom.badRequest(e.message);
            }
        }

    },
	{
		method: 'POST',
        path: '/addJob',
        config: {
            auth: false,
            validate: {
                payload: {
                    title: joi.string().min(5).required(),
                    description: joi.string().required(),
                    phone: joi.string().required()
                }
            }
        },
        handler: async function (request, h) {
            try {
                const newJob = new Job(request.payload);
                const result = await newJob.save();
                return Utils.sanitizeUser(result);
            } catch (e) {
                return Boom.badRequest(e.message);
            }
        }
	},
    {
        method: 'POST',
        path: '/login',
        config: {
            auth: false,
            validate: {
                payload: {
                    email: joi.string().email().required(),
                    password: joi.string().required()
                }
            }
        },
        handler: async function (req, h) {
            const user = await User.findOne({
                email: req.payload.email
            }).exec();
            if (!user) {
                return Boom.badRequest('bad username and/or password');
            }
            const hashedPassword = user.hashPassword(req.payload.password);
            if (hashedPassword !== user.password) {
                return Boom.badRequest('bad username and/or password');
            }
            const sanitizedUser = Utils.sanitizeUser(user);
            sanitizedUser.token = jwt.sign(sanitizedUser, config.jwtKey);
            return sanitizedUser;

        }
    },
	{
        method: 'GET',
        path: '/getJobs',
        config: {
            auth: false
        },
        handler: async function (request, h) {
			const jobs = await Job.find().exec();
			return jobs;
		}
	},
	{
		method: 'POST',
		path: '/save',
        config: {
            auth: false,
			validate: {
                payload: {
					name: joi.string().min(5).required(),
                    email: joi.string().email().required(),
                    password: joi.string().required(),
					title: joi.string().min(5).required(),
                    description: joi.string().required(),
					phone: joi.string().required()
				}
			}
					
        },
        handler: async function (request, h) {
                const newUser = new UserJob(request.payload.name, request.payload.title);
                const result = await newUser.save();
                return Utils.sanitizeUser(result);
		}
	}
];

module.exports = endpoints;