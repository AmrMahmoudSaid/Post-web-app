// ==UserScript==
// @name         ghloc
// @namespace    http://tampermonkey.net/
// @version      0.0.5
// @description  Add ghloc link to GitHub repos.
// @author       pajecawav

// @source       https://gist.github.com/pajecawav/70ffe72bf4aa0968aa9f97318976138f
// @updateURL    https://gist.githubusercontent.com/pajecawav/70ffe72bf4aa0968aa9f97318976138f/raw
// @downloadURL  https://gist.githubusercontent.com/pajecawav/70ffe72bf4aa0968aa9f97318976138f/raw

// @match        *://github.com/*

// @icon         https://icons.duckduckgo.com/ip3/ghloc.vercel.app.ico
// @grant        none
// ==/UserScript==

(function () {
	const LINK_ID = "_ghloc-link";

	function parseCurrentGituhbUrl() {
		const match = location.pathname.match(
			/\/(?<repo>[^/]+\/[^/]+)(\/(tree|blob)\/(?<branch>[^/]+))?(?<path>\/[^\$]+)?/
		);
		if (!match || !match.groups) {
			return null;
		}

		const groups = match.groups;
		if (!groups.branch) {
			const branchSelect = document.querySelector("[data-hotkey='w']");
			if (branchSelect) {
				groups.branch = branchSelect.textContent?.trim();
			}
		}

		return groups;
	}

	function attachLink() {
		if (document.getElementById(LINK_ID)) {
			return;
		}

		const container =
			document.querySelector(".file-navigation") ||
			document.getElementById("blob-more-options-details")?.parentElement;
		if (!container) {
			return;
		}

		const url = parseCurrentGituhbUrl();
		if (!url) {
			return;
		}

		const link = document.createElement("a");
		link.className = "btn ml-2";
		link.textContent = "Stats";
		link.id = LINK_ID;
		link.target = "_blank";
		link.rel = "noopener";
		link.dataset.hotkey = "g l";

		const params = new URLSearchParams();
		let href = `https://ghloc.vercel.app/${url.repo}`;
		if (url.branch) {
			params.append("branch", url.branch);
		}
		const paramsString = params.toString();
		if (paramsString) {
			href += `?${paramsString}`;
		}
		link.href = href;

		container.appendChild(link);
	}

	const observer = new MutationObserver(() => {
		attachLink();
	});
	observer.observe(document.body, { subtree: true, childList: true });
})();
