
// Give the service worker access to Firebase Messaging.
// Note that you can only use Firebase Messaging here, other Firebase libraries
// are not available in the service worker.
importScripts('https://www.gstatic.com/firebasejs/7.14.5/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/7.14.5/firebase-messaging.js');

// Initialize the Firebase app in the service worker by passing in the
// messagingSenderId.
firebase.initializeApp({
  apiKey: "AIzaSyDpg-WwghYJCwSq1Q8nM_5ZW5IY5tLNFmQ",
  authDomain: "shark-abasto-275623.firebaseapp.com",
  databaseURL: "https://shark-abasto-275623.firebaseio.com",
  projectId: "shark-abasto-275623",
  storageBucket: "shark-abasto-275623.appspot.com",
  messagingSenderId: "183752745939",
  appId: "1:183752745939:web:29b89ad4bf08fdcc0c8526",
  measurementId: "G-FTHW54PP2W"
});

// Retrieve an instance of Firebase Messaging so that it can handle background
// messages.
const messaging = firebase.messaging();