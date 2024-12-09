import {useCookies} from "react-cookie";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";
import {MESSAGE} from "@Const";

export function useLogout() {
    const navigate = useNavigate();
    const [cookies, , removeCookie] = useCookies();

    return async () => {
        try {
            // Xóa tất cả các cookies
            const cookieNames = Object.keys(cookies);
            for (const cookieName of cookieNames) {
                await removeCookie(cookieName, {path: '/'});
            }

            // Xóa toàn bộ caches
            if ('caches' in window) {
                const cacheNames = await caches.keys();
                await Promise.all(
                    cacheNames.map(async function (cacheName) {
                        return await caches.delete(cacheName);
                    })
                );
            }

            toast.success(MESSAGE.LOGOUT_SUCCESS);

            navigate('/');
            setTimeout(function() {
                window.location.reload();
            }, 1000);
        } catch (error) {
            console.error('Error during logout:', error);
        }
    };
}
