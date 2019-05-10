const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const session = require('express-session');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(session({
	secret: '@#@$Secret#@$#$',
	resave: false,
	saveUninitialized: true
}));


app.use('/index', express.static(__dirname + '/index.html'));

app.listen(3000, () => { console.info('Express server has started on port 3000'); });


var orders = {}

app.get('/order', (req, res) => {
	orders[req.query.name] = req.query.name;
	res.status(200).send(true);
});

app.get('/orders', (req, res) => {
	res.status(200).send(orders);
});