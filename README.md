# rvlazyscroll


[![](https://jitpack.io/v/spurdow/rvlazyscroll.svg)](https://jitpack.io/#spurdow/rvlazyscroll)


Another Endless RecyclerView


# Gradle Dependency

### Repository

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

Add this in your app `build.gradle` file

```gradle
dependencies {
	...
    compile 'com.github.spurdow:rvlazyscroll:1.0.0'
    }
}
```
### Usage

```java
    RVLazyScroll<Person> endless = new RVLazyScroll<Person>() {
        @Override
        public List<Person> onLoadMore(int offset) {
            // your query here and move it to list Person
            return new ArrayList<Person>(){new Person()};
        }

        @Override
        public void onDoneLoad(List<Person> newListOfdata) {
            // this is where your add it to your adapter
            adapter.addAll(newListOfdata);
        }
    };

    RecyclerView recyclerView ;
    recyclerView.addOnScrollListener(endless);

```