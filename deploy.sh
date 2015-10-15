#!/bin/bash

set -e

# This script is called by .circle.yml to update our doxygen documentation, which is stored on the gh-pages branch of this repo
# It's based on this blog post: http://philipuren.com/serendipity/index.php?/archives/21-Using-Travis-to-automatically-publish-documentation.html

git config --global user.name "$GH_NAME"
git config --global user.email "$GH_EMAIL"

./gradlew javadoc -i
mkdir -p api_docs
git clone -b gh-pages git://github.com/MountainRange/MULE api_docs/html
rm -rf ./api_docs/html/doc
mkdir -p ./api_docs/html/doc
mv -f build/docs/javadoc/* ./api_docs/html/doc
cd api_docs/html
git add ./doc

if ! git diff --quiet --exit-code --cached; then
	git commit -m 'auto-updated api docs'
	git push https://$GH_TOKEN@github.com/MountainRange/MULE gh-pages
else
	echo "Nothing to deploy!"
fi
