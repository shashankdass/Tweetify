# Project 4 - *Twitter Branded Client*

**Twitter Client** is an android app that allows a user to view home and mentions timelines, view user profiles with user timelines, as well as compose and post a new tweet. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: **20** hours spent in total

## User Stories

The following **required** functionality is completed:

* [yes ] The app includes **all required user stories** from Week 3 Twitter Client
* [yes ] User can **switch between Timeline and Mention views using tabs**
  * [yes ] User can view their home timeline tweets.
  * [yes ] User can view the recent mentions of their username.
* [yes ] User can navigate to **view their own profile**
  * [yes ] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [yes ] User can **click on the profile image** in any tweet to see **another user's** profile.
 * [yes ] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
 * [yes ] Profile view includes that user's timeline
* [yes ] User can [infinitely paginate](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView) any of these timelines (home, mentions, user) by scrolling to the bottom

The following **optional** features are implemented:

* [yes ] User can view following / followers list through the profile
* [yes ] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [no ] When a network request is sent, user sees an [indeterminate progress indicator](http://guides.codepath.com/android/Handling-ProgressBars#progress-within-actionbar)
* [yes ] User can **"reply" to any tweet on their home timeline**
  * [yes ] The user that wrote the original tweet is automatically "@" replied in compose
* [yes ] User can click on a tweet to be **taken to a "detail view"** of that tweet
 * [yes ] User can take favorite (and unfavorite) or retweet actions on a tweet
* [yes ] Improve the user interface and theme the app to feel twitter branded
* [yes ] User can **search for tweets matching a particular query** and see results
* [yes ] Usernames and hashtags are styled and clickable within tweets [using clickable spans](http://guides.codepath.com/android/Working-with-the-TextView#creating-clickable-styled-spans)

The following **bonus** features are implemented:

* [yes ] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [yes ] Leverages the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into layout templates.
* [no ] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce view boilerplate.
* [no ] User can view their direct messages (or send new ones)

The following **additional** features are implemented:

* [yes ] List anything else that you can get done to improve the app functionality!
1) Splash screen (twitter branded).
2) Retweet and favorite button states.
## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/MriisdG.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright [2016] [Shashank Dass]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
