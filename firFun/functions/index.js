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

/*exports.addMessage = functions.https.onRequest((req, res) => {
    // Grab the text parameter.
    const original = req.query.text;
// Push the new message into the Realtime Database using the Firebase Admin SDK.
    return admin.database().ref('/Test').push({original: original}).then((snapshot) => {
    // Redirect with 303 SEE OTHER to the URL of the pushed object in the Firebase console.
    return res.redirect(303, snapshot.ref);
});
});


exports.makeUppercase = functions.database.ref('/Test/{somestupidshite}/listenHere/original').onWrite((event) => {
 
const original = event.data.val();
console.log('Uppercasing', event.params.pushId, original);
const uppercase = original.toUpperCase();
return event.data.ref.parent.child('uppercase').set(uppercase);
});*/

//ordinary memeber notification// 
exports.colNotifications = functions.database.ref('/collectives/{colName}/transactions/{i}').onCreate( event => {

    console.log('Transaction Event');
    const colTitle = event.params.colName;
    const details = event.data.child("description").val();
    const paid = event.data.child("value").val();
    const author = event.data.child("payee").val();
    const colRef = admin.database().ref("/collectives/" + colTitle);
    return colRef.once('value').then((snap) => {
        const divisor = snap.child("membersLength").val();
        const owed = parseFloat(paid)/parseFloat(divisor);
        // Create a notification
        const payload = {
            notification: {
                title:author + " at " + colTitle,
                body: details + "\n" + "You owe €" +owed,
                sound: "default"
            },
        };
      //Create an options object that contains the time to live for the notification and the priority
        const options = {
            priority: "high",
            timeToLive: 1
        };
        return admin.messaging().sendToTopic(colTitle.toString(), payload, options);
        }); 
});

exports.addCollectiveIDtoMemberAccountsUpgrade = functions.database.ref('/collectives/{colName}/members/{i}').onCreate((event) => { //only runs when data is updated
    const colID = event.params.colName; //needed to access array at col Event
    const indx = event.params.i;
    const addedEmail = event.data.child("usrEmail").val();
    console.log(addedEmail + " has been added at index " + indx + " to collective " + colID + ".");
    return admin.database().ref('/users').once('value').then((snap) => {
         if(snap.hasChildren()){ //have to make sure it's not triggered on a null set of users
            snap.forEach(function(indvUserSnap) { //FOR LOOP
                var usrEmail = indvUserSnap.child('email').val(); //value of users[i]/email
                if(usrEmail===addedEmail){
                    var userID = indvUserSnap.key;
                    var array = [];
                    var userColRef = admin.database().ref('/users/' + userID + '/myCollectives'); //we need to obtain the array of the user's collectives
                    var i = 0;
                    return userColRef.once("value").then(function(colListSnapshot){ //we are now traversing the user's collective array and re-building it 
                        while(colListSnapshot.child(i.toString()).exists()){
                            array.push(colListSnapshot.child(i.toString()).val());
                            i++;
                        }
                        array.push(colID);
                        console.log(userID + "'s collective list has successfully been updated to include " + colID + ".");
                        return admin.database().ref('/users/' + userID).child('myCollectives').set(array);
                    });
                }
            });
         }
        return;
    });
});

exports.rmCollectiveIDtoMemberAccountsUpgrade = functions.database.ref('/collectives/{colName}/members/{i}').onDelete((event) => { //only runs when data is updated
    const colName = event.params.colName;
    const deletedI = event.params.i;
    const deletedEmail = event.data.previous.child("usrEmail").val();
    console.log("The account with email address " + deletedEmail +  " at index " + deletedI + " in the " + colName + " collective has been deleted from the member array.");
    return admin.database().ref("/users").once("value").then((snap) => {
        if(snap.hasChildren()){
            snap.forEach(function(userIndx){
                var email = userIndx.child('email').val();
                if(email === deletedEmail){
                    var array = [];
                    var i = 0;
                    while(userIndx.child("myCollectives").child(i.toString()).exists()){
                        var colID = userIndx.child("myCollectives").child(i.toString()).val();
                        if(colID !== colName){
                            console.log(colID + " has been repushed.");
                            array.push(colID);
                        }
                        else{
                            console.log("Deleted from array at index " + i);
                        }
                        i++;
                    }
                    console.log("TEST User Key = " + userIndx.key + ". Array is " + array);
                    return admin.database().ref("/users/" + userIndx.key).child("/myCollectives").set(array);
                }
            }); 
        }
        console.log(deletedEmail + " has been successfully deleted from collective " + colName + ".");
        return;
    });  
});

exports.countUsers = functions.database.ref('/users').onWrite((event) => {
    var count = 0;
    return admin.database().ref('/users').once('value').then(function(snapshot){
        snapshot.forEach(function(childSnapshot){
            count++;
        });
         return admin.database().ref('/test').child('countOfUsers').set(count);
    });
});