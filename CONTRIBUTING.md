# Contributing

0. Setup git and [git send-email](https://git-scm.com/docs/git-send-email)
1. Send patches to [rudra.nil.basu.1996@gmail.com](mailto:rudra.nil.basu.1996@gmail.com) along with a cc to the mailing list [airyl@googlegroups.com](mailto:airyl@googlegroups.com)
2. One commit per patch
3. Create an issue before sending the patch
4. Commit messages must be of the form:

```
shortlog: commit message

commit body

fixes #<issue_number>

Signed-off-by: [Your name] <your email>
```

Note: add the `git commit --signoff` flag while committing to get the Signed-off-by line added

5. Use proper commit messages. [Refer this](https://chris.beams.io/posts/git-commit/), mainly:

```
- Separate subject from body with a blank line
- Limit the subject line to 50 characters
- Capitalize the subject line
- Do not end the subject line with a period
- Use the imperative mood in the subject line
- Wrap the body at 72 characters
- Use the body to explain what and why vs. how
```

6. Coding style: Follow [google's coding style](https://google.github.io/styleguide/javaguide.html).
