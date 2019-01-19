let Slack = require('slack-node');
const config = require('./../config/config.json');

webhookUri = config.slack.SLACK_WEBHOOK;

slack = new Slack();
slack.setWebhook(webhookUri);

module.exports = slack;