// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

/*exports.addMessage = functions.https.onRequest((req, res) => {
    // Grab the text parameter.
    const original = req.query.text;
// Push the new message into the Realtime Database using the Firebase Admin SDK.
return admin.database().ref('/messages').push({original: original}).then((snapshot) => {
    // Redirect with 303 SEE OTHER to the URL of the pushed object in the Firebase console.
    return res.redirect(303, snapshot.ref);
});
});*/

exports.addMessage = functions.https.onRequest((req, res) => {
    // Grab the text parameter.
    const original = req.query.text;
// Push the new message into the Realtime Database using the Firebase Admin SDK.
    return admin.database().ref('/Test').push({original: original}).then((snapshot) => {
    // Redirect with 303 SEE OTHER to the URL of the pushed object in the Firebase console.
    return res.redirect(303, snapshot.ref);
});
});

// Listens for new messages added to /messages/:pushId/original and creates an
// uppercase version of the message to /messages/:pushId/uppercase
exports.makeUppercase = functions.database.ref('/Test/{somestupidshite}/listenHere/original').onWrite((event) => {
    // Grab the current value of what was written to the Realtime Database.
    const original = event.data.val();
console.log('Uppercasing', event.params.pushId, original);
const uppercase = original.toUpperCase();
// You must return a Promise when performing asynchronous tasks inside a Functions such as
// writing to the Firebase Realtime Database.
// Setting an "uppercase" sibling in the Realtime Database returns a Promise.
return event.data.ref.parent.child('uppercase').set(uppercase);
});

exports.copyToSomewhereElse = functions.database.ref('/collectivesTesting/{colName}/members/testVal').onWrite((event) => {
    // Grab the current value of what was written to the Realtime Database.
    const uName = event.data.val();
    const collectiveID = event.data.
    console.log('Copying value to same area', event.params.pushId, uName);
    // You must return a Promise when performing asynchronous tasks inside a Functions such as
    // writing to the Firebase Realtime Database.
    // Setting an "uppercase" sibling in the Realtime Database returns a Promise.
    //return event.data.ref.parent.child('copiedValue').set(nName);
    return admin.database().ref('/copiedValues').child('collectiveID').set(collectiveID);
});